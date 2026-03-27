(ns user
  (:require [clojure.repl :refer :all]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]

    ;[com.example.my-project.system :as system]

            [tripleo.el-entry-point :as tep]))

(def system nil)


;(defn init
;  "Constructs the current development system."
;  []
;  (alter-var-root #'system
;                  (constantly (system/system))))
;
(defn start
  "Starts the current development system."
  []
  ;  (alter-var-root #'system system/start))

  (let [c1 (tep/el-make-chan)
        r1 (tep/el-run-loop [c1])]

    (tep/el-nothing)))




;(defn stop
;  "Shuts down and destroys the current development system."
;  []
;  (alter-var-root #'system
;                  (fn [s] (when s (system/stop s)))))
;
;(defn go
;  "Initializes the current development system and starts it running."
;  []
;  (init)
;  (start))
;
;
;
;(defn reset []
;  (stop)
;  (refresh :after 'user/go))
