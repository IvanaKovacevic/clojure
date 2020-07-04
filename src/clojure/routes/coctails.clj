(ns clojure.routes.coctails
  (:require [compojure.core :refer :all]
            [clojure.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [buddy.auth :refer [authenticated?]]
            [struct.core :as st]
            [clojure.models.communication :as db]
            [clj-http.client :as client]
            [cheshire.core :as json]))


(defn get-ingredients [drink]
  (loop [i   1
         acc []]
    (print ((keyword (str "strIngredient" i)) drink))
    (if (= i 15)
      (remove nil? acc)
      (recur (inc i) (conj acc ((keyword (str "strIngredient" i)) drink))))))

(defn get-measure [drink]
  (loop [i   1
         acc []]
    (print ((keyword (str "strMeasure" i)) drink))
    (if (= i 15)
      (remove nil? acc)
      (recur (inc i) (conj acc ((keyword (str "strMeasure" i)) drink))))))

(defn get-data []
  (let [url      "https://www.thecocktaildb.com/api/json/v1/1/random.php"
        response (client/get url)]
    (print url)
    (print "*******************************")
    (cond (= 200 (:status response))
      (let [drink (first (:drinks (json/parse-string (:body response) true)))]
        (print drink)
        (let [ingredients (get-ingredients drink)]
          (print ingredients)
          (layout/render "coctails.html"
                         {:name         (:strDrink drink),
                          :instructions (:strInstructions drink),
                          :photo        (:strDrinkThumb drink),
                          :ingredients  (remove nil? ingredients),
                          :measure      (remove nil? (get-measure drink))}))))))

(defn coctail-page [session]
  (if (layout/is-authenticated? session)
    (get-data)
    (layout/render "login/login.html")))

(defroutes coctails-routes
  (GET "/coctails" request (coctail-page (:session request))))