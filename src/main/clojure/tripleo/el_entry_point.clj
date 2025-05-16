(ns tripleo.el-entry-point
	;; (:require [promesa.core :as p])
	(:require [promesa.exec.csp :as sp]))

(defn el-make-chan [] (sp/chan))

(defn el-run-loop [chans]
	(let [control (sp/chan)]
		;;;; @
		(sp/go                                                    ;; die after 5s, go!!
			(sp/take! (sp/timeout-chan 50))                          ;; was 5000
			(sp/>! control ["bye" nil]))
		(let []
			(sp/go-loop []
									; race: first of control or anything else
									(let [[v ch] (sp/alts! (cons control chans))]
										(when v
											(if (= ch control)
												; if control, print and quit
												(do (if (and (vector? v)
																		 (= (first v) "bye")
																		 (= (second v) nil))
															nil
															(println "1Read" v "from" ch)))
												; otherwise print and go again
												(do (println "2Read" v "from" ch)
														(recur))))))
			3)))

(defn el-nothing []
	; el-make-chan !?
	(let [c1 (el-make-chan)
				c2 (el-make-chan)
				chans (vec '(c1 c2))]
		(do
			(el-run-loop chans)
			(sp/put c1 "hi")
			(sp/put c2 "there")

			(sp/close! c1)
			(sp/close! c2))
		(fn [] (prn chans))))

(defn el-run-mult [chans]
	(let [mx (sp/mult)]
		(dotimes [i (count chans)]
			; was a/go
			(sp/go
				(let [ch (sp/chan)]
					; multi consumer
					(sp/tap! mx ch)
					(println (str "go " i ":") (sp/<! ch))
					(sp/close! ch))))

		(sp/>! mx :a)                                             ; not a take!

		;; Will print to stdout (maybe in different order)
		;;   go 1: :a
		;;   go 2: :a
		))

(comment
	(let [c1 (sp/chan)
				c2 (sp/chan)]
		(sp/go-loop []
								(let [[v ch] (sp/alts! [c1 c2])]
									(when v
										(println "Read" v "from" ch)
										(recur))))

		(sp/>! c1 "hi")
		(sp/>! c2 "there")

		(sp/close! c1)
		(sp/close! c2)))


(comment
	;; Prints (on stdout, possibly not visible at your repl):
	;;   Read hi from #<promesa.exec.csp.channel.Channel ...>
	;;   Read there from #<promesa.exec.csp.channel.Channel ...>

	;; Since go blocks are lightweight processes not bound to threads, we
	;; can have LOTS of them! Here we create 1000 go blocks that say hi on
	;; 1000 channels. We use alts! to read them as they're ready.

	(let [n 1000
				cs (repeatedly n sp/chan)
				begin (System/currentTimeMillis)]

		;; Add pending put operation to all channels
		(doseq [c cs] (sp/put! c "hi"))

		(dotimes [i n]
			(let [[v c] (sp/alts! cs)]
				(assert (= "hi" v))))

		;; Close all channels
		(run! sp/close! cs)

		(println "Read" n "msgs in" (- (System/currentTimeMillis) begin) "ms")))

