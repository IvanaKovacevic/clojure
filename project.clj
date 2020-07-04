(defproject traffic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [conman "0.8.3"]
                 [funcool/struct "1.3.0"]
                 [mysql/mysql-connector-java "8.0.12"]
                 [migratus "1.2.0"]
                 [org.clojure/clojurescript "0.0-3165"]
                 [metosin/ring-http-response "0.9.1"]
                 [ring-server "0.4.0"]
                 [selmer "1.12.5"]
                 [ring-webjars "0.2.0"]
                 [mount "0.1.15"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.webjars/bootstrap "4.2.1"]
                 [org.webjars/font-awesome "5.6.3"]
                 [org.webjars/jquery "3.3.1-1"]
                 [lib-noir "0.9.9"]
                 [liberator "0.10.0"]
                 [ring/ring-json "0.4.0"]
                 [korma/korma "0.4.3"]
                 [yesql "0.5.3"]
                 [com.fzakaria/slf4j-timbre "0.3.12"]
                 [com.cerner/clara-rules "0.14.0"]
                 [buddy/buddy-auth "2.1.0"]
                 [yogthos/config "1.1.1"]
                 [clj-http "3.9.1"]
                 [bcrypt-clj "0.3.3"]
                 [cheshire "5.8.1"]
                 [http-kit "2.2.0"]]
  :require [config.core :refer [env]]
  :jvm-opts ["-Dconf=dev-config.edn"]

  :plugins [[lein-ring "0.12.5"]
            [migratus-lein "0.7.0"]]
  :migratus {:store         :database
             :migration-dir "migrations"
             :db            (clojure.edn/read-string (slurp "config.edn"))}
  :ring {:handler clojure.handler/app
         :init    clojure.handler/init
         :destroy clojure.handler/destroy}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
