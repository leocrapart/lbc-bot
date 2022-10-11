(ns lbc-bot.core
  (:gen-class)
  (:require [robot.core :as r]
            [dk.ative.docjure.spreadsheet :as xl]
            [clojure.edn :as edn]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (deposer-10-annonces-pro))

(-main)

(defn sleep [ms]
  (Thread/sleep ms))

(defn click [x y]
  (sleep 100)
  (r/mouse-move! x y)
  (sleep 100)
  (r/mouse-click!)
  (sleep 100))

(defn paste [text]
  (sleep 100)
  (r/clipboard-put! text)
  (sleep 100)
  (r/hot-keys! [:ctrl :v])
  (sleep 100))


;; data interface 

(def sheet
  (->> (xl/load-workbook "data.xlsx")
       (xl/select-sheet "Feuil1")))

(defn cell [cell-str]
  (let [col (first cell-str)
        row (edn/read-string (str (last cell-str)))]
    (->> sheet
         (xl/select-cell cell-str)
         (xl/read-cell))))

; (cell "A1")

(defn title [n]
  (cell (str "A" (+ n 1))))

; (title 1)
; (title 5)
; (title 10)

(defn description [n]
  (cell (str "B" (+ n 1))))

; (description 1)

(defn image [n]
  (cell (str "C" (+ n 1))))

(defn location [n]
  (cell (str "D" (+ n 1))))

(def phone1
  (cell "G2"))

(def phone2
  (cell "H2"))

(def phone-id-to-phone
{1 phone1
 2 phone2
})

(defn phone [n]
  (let [phone-id (int (cell (str "E" (+ n 1))))]
    (phone-id-to-phone phone-id)))

; (phone 1)
; (phone 2)
; (phone 3)
; (phone 10)

(defn annonce [n]
{:title (title n)
 :description (description n)
 :image (image n)
 :location (location n)
 :phone (phone n)})

; (annonce 1)
; (annonce 10)
; ((annonce 10) :location)


;;--- end data interface ---


;;; init
; ;; go to leboncoin.fr
; (click 274 47)
; (sleep 500)
; (paste "leboncoin.fr")
; (r/type! :enter)
; (sleep 3000)

; ; accept cookie
; (click 873 715)
; (sleep 500)

;; login
; (click 1287 130)
; (sleep 1000)
; ; email
; (click 810 453)
; (paste "bretonphilippe643@gmail.com")
; ; password
; (click 810 550)
; (paste "Sdfghj234")
; ; connect
; (click 810 650)
; (sleep 1000)

(defn deposer-annonce-pro [annonce]
  ; deposer annonce url
  (click 274 47)
  (sleep 500)
  (paste "leboncoin.fr/deposer-une-annonce")
  (r/type! :enter)
  (sleep 3000)

  ; prestation de services
  ; categories 
  ; services > prestation de services
  (click 400 800)
  (click 692 439)
  (sleep 1000)
  ; continue
  (click 1000 400)

  ; title
  (click 300 300)
  (paste (annonce :title))

  ;; description
  (click 313 513)
  ; (paste "description description")
  (paste (annonce :description))
  ; unfocus 
  (click 100 400)
  ; continue
  (r/scroll! 2)
  (sleep 1000)
  (click 1000 800)
  (sleep 1000)

  ; price
  ; no price

  ; continue
  (click 1000 370)
  (sleep 1000)

  ;; image
  ; load image button
  (click 380 340)
  (sleep 1000)
  ; path bar
  (click 460 50)
  (sleep 1000)
  ; path to image folder
  (paste "C:\\Users\\leocr\\Desktop\\lbc-images")
  (r/type! :enter)
  (sleep 1000)
  ; file name
  ; click file name input
  (click 310 414)
  ; insert file name
  (paste (annonce :image))
  (r/type! :enter)
  ; wait image loading
  (sleep 4000)
  ; continue
  (click 1000 640)
  (sleep 1000)

  ; wait image loading
  (sleep 4000)
  ; continue
  (click 1000 640)
  (sleep 1000)

  ; location
  (click 400 275)
  (click 400 275)
  (click 400 275)
  (paste (annonce :location))
  (r/type! :enter)
  (sleep 1000)
  ; continue
  (click 400 275)
  (r/type! :enter)
  (sleep 1000)

  ; phone
  (click 900 575)
  (click 900 575)
  (paste (annonce :phone))

  ; ;;;;;;;;;;;;;;;;;;;;; 
; ; ; continue
; ;   Click, 1000, 730
; ;   Sleep, 2000

; ; ; no boost
; ;   Click, 1200, 830
; ;   Sleep, 500

; ; ; back to homepage
; ;   Click, 330, 127
; ;   Sleep, 1000

  ; idle position, tells the bot finished
  (r/mouse-move! 100 100))


; (deposer-annonce-pro (annonce 9))


(defn deposer-10-annonces-pro []
  (deposer-annonce-pro (annonce 1))
  (sleep 10000)
  (deposer-annonce-pro (annonce 2))
  (sleep 330000)
  (deposer-annonce-pro (annonce 3))
  (sleep 330000)
  (deposer-annonce-pro (annonce 4))
  (sleep 330000)
  (deposer-annonce-pro (annonce 5))
  (sleep 330000)
  (deposer-annonce-pro (annonce 6))
  (sleep 330000)
  (deposer-annonce-pro (annonce 7))
  (sleep 330000)
  (deposer-annonce-pro (annonce 8))
  (sleep 330000)
  (deposer-annonce-pro (annonce 9))
  (sleep 330000)
  (deposer-annonce-pro (annonce 10)))


;; start stop
(defn f-deposer-annonce-pro [n]
  (while true
    (if-not (Thread/interrupted)
      ; ... do your work
      (deposer-annonce-pro (annonce n))
      ; ... ------------
      (throw (InterruptedException. "Function interrupted...")))))

(defn t-annonce [n] 
  (Thread. (fn []
            (try
              (while true
                (f-deposer-annonce-pro n))
              (catch InterruptedException e
                (println (.getMessage e)))))))


(defn f-deposer-10-annonces-pro []
  (while true
    (if-not (Thread/interrupted)
      ; ... do your work
      (deposer-10-annonces-pro)
      ; ... ------------
      (throw (InterruptedException. "Function interrupted...")))))

(def t-10-annonces
  (Thread. (fn []
            (try
              (while true
                (f-deposer-10-annonces-pro))
              (catch InterruptedException e
                (println (.getMessage e)))))))

(def t-annonce-1
  (t-annonce 1))

; (.start t-annonce-1)
; (.interrupt t-annonce-1)

(.start t-10-annonces)
(.interrupt t-10-annonces)

;; excel => v
;; 10 => v
;; stop the bot => v
