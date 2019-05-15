import React from "react"

function Header(props) {

return (
  <div>
    <input type="button" value="Banners" onClick={() => { props.updateData(`Banners`)}} />
    <input type="button" value="Categories" onClick={() => { props.updateData(`Categories`)}} />
    <hr/>
  </div>
 );
}

export default Header