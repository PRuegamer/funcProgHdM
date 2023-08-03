(def todos
  [{:id 1 :task "Buy groceries" :completed false}
   {:id 2 :task "Do laundry" :completed false}
   {:id 3 :task "Clean the house" :completed true}])

(defn add-todo [todos task]
  ;; "Adds a new todo item to the list."
  (let [new-id (->> todos
                    (map :id)
                    (apply max)
                    (inc))
        new-todo {:id new-id :task task :completed false}]
    (conj todos new-todo)))

(defn complete-todo [todos id]
  ;; "Marks a todo item as completed."
  (map #(if (= (:id %) id)
           (assoc % :completed true)
           %)
       todos))

(defn incomplete-todo [todos id]
  ;; "Marks a todo item as incomplete."
  (map #(if (= (:id %) id)
           (assoc % :completed false)
           %)
       todos))

(defn edit-todo [todos id new-task]
  ;; "Edits the task description of a todo item."
  (map #(if (= (:id %) id)
           (assoc % :task new-task)
           %)
       todos))

(defn delete-todo [todos id]
  ;; "Deletes a todo item from the list."
  (filter #(not= (:id %) id) todos))

(defn sort-todos [todos sort-by]
  ;; "Sorts the list of todos by task description or completion status."
  (if (= sort-by :task)
    (sort-by :task todos)
    (sort-by :completed todos)))

(defn filter-todos [todos completed]
  ;; "Filters the list of todos by completion status."
  (filter #(= (:completed %) completed) todos))

(defn search-todos [todos search-term]
  ;; "Searches the list of todos for a specific task."
  (filter #(re-find (re-pattern search-term) (:task %)) todos))

(defn print-todos [todos]
  ;; "Prints the current list of todos."
  (doseq [todo todos]
    (println (str (:id todo) ". " (:task todo) " - " (if (:completed todo) "completed" "incomplete")))))

;; Example usage:
;; Add a new todo item
(def todos (add-todo todos "Walk the dog"))

;; Mark a todo item as completed
(def todos (complete-todo todos 2))

;; Edit a todo item
(def todos (edit-todo todos 1 "Buy groceries and milk"))

;; Delete a todo item
(def todos (delete-todo todos 3))

;; Sort the list by task description
(def todos (sort-todos todos :task))

;; Sort the list by completion status
(def todos (sort-todos todos :completed))

;; Filter the list by completion status
(def completed-todos (filter-todos todos true))

;; Search for a specific task
(def search-results (search-todos todos "groceries"))

;; Print the current list of todos
(print-todos todos)

(print-todos completed-todos)

(print-todos search-results)

(complete-todo search-results 1)