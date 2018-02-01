(ns dispatch-n-bug-mwe.events
  (:require [re-frame.core :as re-frame]
            [dispatch-n-bug-mwe.db :as db]))

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-fx
  ::action
  (fn [_ _]
    {:dispatch-n (list [::action-trigger 1]
                       [::action-trigger 1])}))

(re-frame/reg-event-fx
  ::action-trigger
  (fn [{:keys [db]} [_ to-add]]
    {:dispatch [::action-db (+ (:value db) to-add)]}))

(re-frame/reg-event-db
  ::action-db
  (fn [db [_ value]]
    (assoc db :value value)))
