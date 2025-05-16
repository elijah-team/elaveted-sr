(ns evaleted-lein.core
  (:require
    [donut.system :as ds])
  (:gen-class)
  (:import (java.util.concurrent TimeUnit)
           (tripleo.elijah Main)
           (tripleo.elijah_congenial.process ZQ)
    ;(tripleo.elijah_congenial.test_support InstrumentalTest1Test)
           ))

(def system
  {::ds/defs
   {:app
    {:printer
     #::ds{:start  (fn [_]
                     (.get (future
                             (ds/signal system :elijah/args)
                             (loop []
                               (println "hello!")
                               (Thread/sleep 1000)
                               (recur)))))
           :elijah (fn [{:keys [:elijah/args]}]
                     (let [m (Main.)]
                       (.get (future
                               (doto m (.main args))))))
           :stop   (fn [{:keys [::ds/instance]}]
                     (future-cancel instance))}}}

   ::ds/signals [{:elijah/args {:order :topsort}}
                 {:elijah/start {:order :topsort}}
                 ;:your.app/validate {:order :reverse-topsort}
                 ]})
(defn main-signal [system]
  (ds/signal system :elijah/args))

;(def system2
;  {::ds/defs
;   {:services
;    {:printer #::ds{:start                                  ;(fn [_] (print "donuts are yummy!"))}}
;                    ;{:compiler #::elijah{:start
;                    (fn [_] (print "pie tastes great too!"))}}
;    }}
;  ;::ds/signals {:elijah/start {:order :topsort}}
;  )
;
;(ds/signal system2 ::ds/start)

(defn start []
  (ds/signal system :elijah/start)
  ;::elijah/start)
  )

(defn zzqq [a b c]
  (ZQ/printStartMessage a b c))

(defn -main2
  "hmm"
  [& args]

  ;(.main (Main.) (if (nil? args) (list) args))

  (if (true? true)
    (do
      ;(update {} ::ds/signals :elijah/args (fn [x] (prn x)))

      (zzqq "evaleted-lein::SYSTEM2 with donut system" 5 TimeUnit/SECONDS)

      (let [running-system (ds/signal system ::ds/start)]
        (main-signal system)
        (Thread/sleep 5000)

        (ds/signal running-system ::ds/stop))))

  (println "All done."))

(defn -main []
  (let [file-name "test/demo-el-normal/main2"
        active true]
    (if (true? active)
      (Main/main2 (list file-name))))

  ;(doto (InstrumentalTest1Test.)
  ;  (.chunkyExample))
  )
