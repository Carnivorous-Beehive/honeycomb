(ns honeycomb.virtual-dom)

(defn create-element [tag & {:keys [attrs children] :or {attrs '{} children '[]}}]
  (js/Object.create
   (VirtualDOMElement.
    tag
    attrs
    children)))

(defrecord VirtualDOMElement [tag attrs children])

(defprotocol RenderProtocol
  "Render DOM NodeTypes
  see: https://developer.mozilla.org/en-US/docs/Web/API/Node/nodeType"
  (render [node] "Render method for a given node"))

(extend-protocol RenderProtocol
  string
  (render [text] (.createTextNode js/document text))

  VirtualDOMElement
  (render [node]
    (let [e (.createElement js/document (name (:tag node)))]
      (doseq [[attr value] (:attrs node)]
        (.setAttribute e attr value))
      (doseq [child (seq (:children node))]
        (.appendChild e (render child)))
      e)))
