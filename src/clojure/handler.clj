(ns clojure.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [clojure.routes.home :refer [home-routes]]
            [clojure.routes.login_register :refer [login-register-routes]]
            [clojure.routes.profile :refer [profile-routes]]
            [clojure.routes.recipe :refer [recipe-routes]]
            [clojure.routes.coctails :refer [coctails-routes]]
            [clojure.routes.add_ingr :refer [ingr-routes]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [ring.middleware.flash :refer [wrap-flash]]
            [ring.middleware.resource :refer [wrap-resource]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.accessrules :refer [wrap-access-rules success error]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.params :refer [wrap-params]]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.accessrules :refer [restrict]]
            [ring.middleware.json :refer [wrap-json-response]]))

(defn start [request]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    "Hello Clojure!"})

(defn on-error [request response]
  {:status  403
   :headers {"Content-Type" "text/plain"}
   :body    (str "Access to " (:uri request) " is not authorized")})

(defroutes app-routes
  (route/not-found "Not Found")
  (route/resources "/"))

(def backend (session-backend))

(defn destroy [])

(defn init []
  (System/setProperties
   (doto (java.util.Properties. (System/getProperties))
         (.put "com.mchange.v2.log.MLog" "com.mchange.v2.log.FallbackMLog")
         (.put "com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL" "OFF")))
  (selmer.parser/cache-off!))

(def app
  (->
    (routes
     home-routes login-register-routes profile-routes recipe-routes ingr-routes coctails-routes app-routes
     (wrap-routes wrap-defaults api-defaults))
    (wrap-json-response)
    (handler/site)
    (wrap-authentication backend)
    (wrap-authorization backend)
    (wrap-flash)
    (wrap-params)
    (wrap-webjars)
    (wrap-resource "public")))