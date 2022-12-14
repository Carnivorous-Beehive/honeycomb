(ns honeycomb.virtual-dom)

(defn create-element [tag & {:keys [attrs children] :or {attrs '{} children '[]}}]
  (js/Object.create
   (VirtualDOMElement. tag attrs children)))

(defrecord VirtualDOMElement [tag attrs children])

(defprotocol RenderProtocol
  "Render DOM NodeTypes
  see: https://developer.mozilla.org/en-US/docs/Web/API/Node/nodeType"
  (render [node] "Render method for a given node"))

(extend-protocol RenderProtocol
  string
  (render [text] (.createTextNode js/document text))

  VirtualDOMElement
  (render [element]
    (let [$e (.createElement js/document (name (:tag element)))]
      (doseq [[attr value] (:attrs element)]
        (.setAttribute $e (name attr) (name value)))
      (doseq [child (seq (:children element))]
        (.appendChild $e (render child)))
      $e)))
