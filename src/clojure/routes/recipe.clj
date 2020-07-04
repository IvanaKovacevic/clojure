(ns clojure.routes.recipe
  (:require [compojure.core :refer :all]
            [clojure.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [buddy.auth :refer [authenticated?]]
            [struct.core :as st]
            [clojure.models.communication :as db]))

(defn recipe-page [params]
  (if (layout/is-authenticated? params)
    (layout/render "recipe.html"
                   {:types       (db/get-types)
                    :typesA      (db/get-typesA)
                    :ingredients (db/get-ingredients)})
    (layout/render "login/login.html")))

(def recipe-schema
  [[:name st/required st/string]
   [:description st/required st/string]
   [:ingredient st/required st/string]])

(defn add-amount [recipe-id params i]
  (db/save-amount
   {:recipe recipe-id,
    :ingr (nth (:ingredient params) i),
    :type   (nth (:typeA params) i),
    :amount (nth (:amount params) i)}))

(defn add-recipe [request]
  (db/add-recipe
   {:name        (:name (:params request)),
    :description (:description (:params request)),
    :recipe_type (:type (:params request)),
    :user        (:id (:identity (:session request)))})
  (let [recipe-id (:id (last (db/get-recipes)))]
    (let [n (count (:ingredient (:params request)))]
      (loop [i 0]
        (when (< i n)
          (add-amount recipe-id (:params request) i)
          (recur (+ i 1))))))
  (-> (redirect "/home")
      (assoc :session (:session request))))

(defroutes recipe-routes
  (GET "/recipe" request (recipe-page (:session request)))
  (POST "/recipe" request (add-recipe request)))