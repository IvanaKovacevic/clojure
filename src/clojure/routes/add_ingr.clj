(ns clojure.routes.add_ingr
  (:require [compojure.core :refer :all]
            [clojure.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [buddy.auth :refer [authenticated?]]
            [struct.core :as st]
            [clojure.models.communication :as db]))

(defn ingr-page [session]
  (if (layout/is-authenticated? session)
    (layout/render "add-ingr.html" {:ingr (db/get-ingredients)})
    (layout/render "login/login.html")))

(defn add-ingr [req]
  (print "************")
  (print req)
  (print "************")
  (print (:ingredient (:params req)))
  (if (layout/is-authenticated? (:session req))
    ((db/add-ingr {:name (:ingredient (:params req))})
      (:ingr (db/get-ingredients))
      (ingr-page (:session req)))
    (layout/render "login/login.html")))

(defroutes ingr-routes
  (GET "/add_ingr" request (ingr-page (:session request)))
  (POST "/add_ingr" request (add-ingr request)))

