(defproject todolist "0.1.0-SNAPSHOT"
  :description "A Leiningen project for JSON parsing and user input"
  :dependencies [[org.clojure/clojure "1.10.3"],
                 [org.clojure/data.json "2.4.0"]]
  :main ^:skip-aot todolist.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})