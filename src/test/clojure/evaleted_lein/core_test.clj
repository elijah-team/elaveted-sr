(ns evaleted-lein.core-test
  ;  (:use   [clojure.pprint :refer pp])
  (:require [clojure.test :refer :all]
            [evaleted-lein.core :refer :all]

            )
  (:import (java.util ArrayList)
           (tripleo.elijah ElijahCon Main)
    ;(tripleo.elijah_congenial.test_support InstrumentalTest1Test)
           (tripleo.elijah.comp.i CompilerController)
           (tripleo.elijah_clojure.example CljExampleMain)
           (tripleo.elijah_durable_elevated.factory NonOpinionatedBuilder)))

(deftest a-test
  (testing "FIXME, I fail."
    ;(doto (ExampleTEst.)
    ;	(.chunkyExample))

    (is (= 1 1))))

(deftest b-test
  (testing "Entry point for test/demo-el-normal/main2"
    (let [f "test/demo-el-normal/main2"
          args (ArrayList.)
          ccl (ArrayList.)
          cca (atom nil)

          cfg {:foo                       :bar
               "CompilerController-deref" (fn [] '(deref cca 300 nil))
               "CompilerController"       (fn [x]
                                            (swap! cca 'x))}
          ctl (Main/main3 (list f) cfg)]
      (is (= (.errorCount ctl) 1)))))

(defn hello []
  ;(println 6)
  "heavenly")

(deftest c-test
  (testing "FIXME, 3"
    (is (= "heavenly" (CljExampleMain/callClojure "evaleted-lein.core-test" "hello")))))

(deftest d-test
  (testing "FIXME, 4"
    (is (= 1 1)
        (= "heavenly" (CljExampleMain/callClojure2)))))
