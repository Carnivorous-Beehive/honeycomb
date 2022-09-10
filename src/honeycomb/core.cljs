(ns honeycomb.core
  (:require [honeycomb.virtual-dom :as vdom]))

;; render the dom!
(let [dom (vdom/create-element
           :div
           :attrs {:id :app}
           :children ["Hello World"
                      (vdom/create-element
                       :img
                       :attrs {:src "https://media.giphy.com/media/cuPm4p4pClZVC/giphy.gif"})])
      app (vdom/render dom)]
  (println dom)
  app)
