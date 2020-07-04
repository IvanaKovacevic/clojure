(ns clojure.routes.profile
  (:require [compojure.core :refer :all]
            [clojure.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [struct.core :as st]
            [clojure.models.communication :as db]))

(def user-schema
  [[:username st/required st/string]
   [:name st/required st/string]
   [:surname st/required st/string]
   [:password
    [st/required :message "This field is required"]
    st/string
    {:message  "Password must contain at least 6 characters"
     :validate #(> (count %) 6)}]])

(defn user-validation? [user]
  (first (st/validate user user-schema)))

(defn find-user-by-id [session]
  (first (db/get-user-by-id {:id (get-in session [:identity :id])})))

(defn profile-page
  [session]
  (layout/render "profile.html" (find-user-by-id session)))

(defn submit-profile [{:keys [params session]}]
  (let [errors (user-validation? params)]
    (print errors)
    (if (empty? errors)
      (let [rows (->> (assoc params :id (get-in session [:identity :id]))
                      db/update-user)]
        (if (= rows 1)
          (layout/render "profile.html"
                         (assoc (find-user-by-id session)
                                :message
                                {:type "success" :message "User information was successfully updated."}))
          (layout/render "profile.html"
                         {assoc    params
                          :message {:type "danger" :message "User information was not updated, try again!"}})))
      (layout/render "profile.html" (assoc params :errors errors)))))

(defroutes profile-routes
  (GET "/profile" request (profile-page (:session request)))
  (POST "/profile" request (submit-profile request)))