(ns clojure.models.communication
  (:require [clojure.java.jdbc :as sql]
            [korma.core :as k]
            [korma.db :refer [defdb mysql]]
            [clj-time.coerce :as c]
            [clj-time.core :as t])
  (:import java.sql.DriverManager))

(def db-config (clojure.edn/read-string (slurp "config.edn")))

(defdb db (mysql db-config))

(k/defentity user
  (k/table :user))

(k/defentity recipe_type
  (k/table :recipe_type))

(defn get-user-by-username [params]
  (k/select user
            (k/where params)))

(defn save-user [params]
  (k/insert user
            (k/values params)))

(defn get-user-by-id [params]
  (k/select user
            (k/where params)))

(defn update-user [params]
  (k/update user
            (k/set-fields params)
            (k/where {:id (:id params)})))

(defn get-types []
  (k/select recipe_type))

(k/defentity amount_type
  (k/table :amount_type))

(defn get-typesA []
  (k/select amount_type))

(k/defentity recipe
  (k/table :recipe))

(defn add-recipe [params]
  (k/insert recipe
            (k/values params)))

(defn get-recipes []
  (k/select recipe
            (k/order :id :ASC)))

(k/defentity ingredients
  (k/table :ingredients))

(k/defentity amount
  (k/table :amount))

(defn save-amount [params]
  (k/insert amount
            (k/values params)))

(defn get-ingredients []
  (k/select ingredients))

(defn add-ingr [params]
  (k/insert ingredients
            (k/values params)))

(k/defentity recipe
  (k/table :recipe))

(defn get-recipes []
  (k/select recipe
            (k/fields :* [:recipe_type.name :type])
            (k/join recipe_type (= :recipe_type.ID :recipe.recipe_type))))

(defn find-recipe [params]
  (k/select recipe
            (k/fields :* [:recipe_type.name :type])
            (k/join recipe_type (= :recipe_type.ID :recipe.recipe_type))
            (k/where {:id (:id params)})))


(defn get-ingredients-for-recipe [params]
  (k/select amount
            (k/fields :* [:amount_type.name :aname] [:ingredients.name :iname])
            (k/join amount_type (= :amount_type.id :amount.type))
            (k/join ingredients (= :ingredients.id :amount.ingr))
            (k/where {:recipe (:id params)})))

