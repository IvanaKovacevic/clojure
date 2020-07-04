(ns clojure.routes.home
  (:require [compojure.core :refer :all]
            [clojure.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [buddy.auth :refer [authenticated?]]
            [struct.core :as st]
            [clojure.models.communication :as db]))

(defn home-page
  [session]
  (if (layout/is-authenticated? session)
    (layout/render "home.html" {:recipes (db/get-recipes)})
    (layout/render "login/login.html")))

(defn get-recipe-page [page params session & [message]]
  (print "****************" params)
  (print
   (db/find-recipe params))
  (layout/render "view-recipe.html"
                 {:ingr        (db/get-ingredients-for-recipe params),
                  :description (:description (first (db/find-recipe params))),
                  :name        (:name (first (db/find-recipe params)))}))

(defn get-recipe [{:keys [params session]} & [message]]
  (if (layout/is-authenticated? session)
    (get-recipe-page "view-recipe.html" params session message)
    (layout/render "login/login.html")))


(defroutes home-routes
  (GET "/home" request (home-page (:session request)))
  (GET "/" request (home-page (:session request)))
  (GET "/viewRecipe/:id" request (get-recipe request)))
