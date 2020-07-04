(ns clojure.layout
  (:require [selmer.parser :as parser]
            [buddy.auth :refer [authenticated?]]
            [ring.util.http-response :refer [content-type ok]]))

(parser/set-resource-path! (clojure.java.io/resource "views"))

(parser/cache-off!)

(defn is-admin? [session]
  (and (authenticated? session)
       (= true (:admin (:identity session)))))

(defn is-authenticated? [session]
  (authenticated? session))

(defn render
  [template & [params]]
  (content-type
   (ok
    (parser/render-file
     template
     (assoc params
            :page template)))
   "text/html; charset=utf-8"))