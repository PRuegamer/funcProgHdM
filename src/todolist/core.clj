(ns todolist.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json]))

;; todolist

;; Read JSON data from a file
(defn read-json-file [filename]
  (with-open [rdr (io/reader (io/resource filename))]
    (json/read rdr :key-fn keyword)))

;; Write JSON data to a file
(defn write-json-file [filename data]
  (with-open [wrtr (io/writer (io/resource filename))]
    (json/write data wrtr)))

;; initialize todolist from JSON file as atom

(def todolist (atom 
               (let [data (read-json-file "todolist.json")]
                 (:todolist data))))

;; from todolist print all todos in a string

(defn print-todolist [todolist]
  (doseq [[i value] (map-indexed vector todolist)]
    (println (str (inc i) ". " (if (= true (:status value)) "[x]" "[ ]") " - "(:task value)))))

;; Add a new todo to the todolist
(defn add-todo [task]
  (println (str "Adding task " task))
  (swap! todolist conj {:task task :status false}))

;; Remove a todo from the todolist at a given index
(defn remove-todo-by-index [index]
  (println (str "Removing task " index " from todolist"))
  (let [todo (nth @todolist (dec index))]
   (swap! todolist #(remove (partial = todo) %))))

;; remove todos from todolist that are completed

(defn remove-completed-todos []
  (println "Removing completed todos")
  (swap! todolist #(remove :status %)))

;; edit a todo in the todolist at a given index

(defn edit-todo [key value index]
  (let [todo (nth @todolist (dec index))
        updated-todo (assoc todo key value)]
    (swap! todolist #(replace {todo updated-todo} %))))

(def complete-todo
  (partial edit-todo :status true))

(def incomplete-todo
  (partial edit-todo :status false))

(defn edit-task [index task]
  (edit-todo :task task index))

;; Sort the todolist by task name, status
(defn sort-todolist [key]
  (swap! todolist #(sort-by key %)))


;; Search for a task by name or keyword and print the results
(defn search-todolist [search-term]
  (println (str "Searching for " search-term))
  (filter #(re-find (re-pattern search-term) (:task %)) @todolist))

;; Main function

(defn -main []  
  (loop []
    (println "Enter a command (list, search, add, remove, remove-completed, complete, incomplete, sort, search, exit):")
    (let [command (read-line)]
      (cond
        (= command "list") (do
                             (println "Todolist:")
                             (print-todolist @todolist))
        (= command "search") (do
                               (println "Enter a search term:")
                               (print-todolist (search-todolist (read-line))))
        (= command "add") (do
                            (println "Enter a task:")
                            (add-todo (read-line)))
        (= command "remove") (do
                               (println "Enter an index:")
                               (remove-todo-by-index (Integer/parseInt (read-line))))
        (= command "edit") (do
                             (println "Enter an index:")
                             (let [index (Integer/parseInt (read-line))]
                               (println "Enter a task:")
                               (edit-task index (read-line))))
        (= command "remove-completed") (do
                                         (println "Removing completed todos")
                                         (remove-completed-todos))
        (= command "complete") (do
                                 (println "Enter an index:")
                                 (complete-todo (Integer/parseInt (read-line))))
        (= command "incomplete") (do
                                   (println "Enter an index:")
                                   (incomplete-todo (Integer/parseInt (read-line))))
        (= command "sort") (do
                             (println "Enter a field (task or status):")
                             (sort-todolist (keyword (read-line)))))
      (when (not= command "exit")
        (recur))))
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (add-todo "Learn Clojure")
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Editing task 2")
  ;; (edit-task 2 "Buy meat")
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Marking task 2 as completed")
  ;; (complete-todo 2)
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Marking task 3 as incomplete")
  ;; (incomplete-todo 3)
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Sorting todolist by task name")
  ;; (sort-todolist :task)
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Sorting todolist by status")
  ;; (sort-todolist :status)
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Searching for 'meat'")
  ;; (print-todolist (search-todolist "meat"))
  ;; (println "--------------------")
  ;; (println "Searching for 'Buy'")
  ;; (print-todolist (search-todolist "Buy"))
  ;; (println "--------------------")
  ;; (remove-todo-by-index 1)
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (remove-completed-todos)
  ;; (print-todolist @todolist)
  ;; (println "--------------------")
  ;; (println "Saving todolist to file")
  ;; (write-json-file "todolist.json" {:todolist @todolist})
  ;; (println "--------------------")
  )



