(ns clojure.routes.login_register
  (:require [compojure.core :refer :all]
            [clojure.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [buddy.auth :refer [authenticated?]]
            [struct.core :as st]
            [clojure.models.communication :as db]))

(def login-schema
  [[:username st/required st/string]
   [:password st/required st/string]])

(def register-schema
  [[:username st/required st/string]
   [:name st/required st/string]
   [:surname st/required st/string]
   [:password
    [st/required :message "This field is required"]
    st/string
    {:message  "Password must contain at least 6 characters"
     :validate #(> (count %) 6)}]])

(defn login-validation? [params]
  (st/valid?
   {:username (:username params)
    :password (:password params)}
   login-schema))

(defn register-validation? [user]
  (first (st/validate user register-schema)))

(defn login-page
  [& [error]]
  (layout/render "login/login.html"
                 {:title "Log in"
                  :error error}))

(defn register-page
  [& [error]]
  (layout/render "login/register.html"
                 {:title "Register"
                  :error error}))

(defn get-user-by-username [params]
  (login-validation? params)
  (-> (select-keys params [:username])
      (db/get-user-by-username)
      (first)))

(defn save-user [user]
  (-> (db/save-user user)))

(defn submit-login [{:keys [params session]}]
  (let [user (get-user-by-username params)]
    (if (and user (= (:password params) (:password user)))
      (assoc (redirect "/home") :session (assoc session :identity user))
      (login-page "Error! Enter username and password"))))

(defn submit-register [{:keys [params session]}]
  (let [errors (register-validation? params)]
    (if (empty? errors)
      (let [user (get-user-by-username params)]
        (if (not-empty user)
          (layout/render "login/register.html" (assoc params :errors errors))
          (do
            (save-user params)
            (redirect "/login"))))
      (layout/render "login/register.html" (assoc params :errors errors)))))

(defn logout [request]
  (-> (redirect "/login")
      (assoc :session {})))

(defroutes login-register-routes
  (GET "/register" [] (register-page))
  (POST "/register" request (submit-register request))
  (GET "/login" [] (login-page))
  (POST "/login" request (submit-login request))
  (GET "/logout" request (logout request)))