import React, {Component} from 'react';

class CategoriesSearch extends Component {

  constructor(props) {
        super(props)


       }


 dataSearch = (event) => {
     this.props.updateFilterText(event.target.value.toLowerCase())
         }

  render() {

       const filterText = this.props.filterText

         return (<div>
                  <input type="text"
                         name="search"
                         value={filterText}
                         onChange={this.dataSearch}
                         placeholder="Enter category name..." /><br/>
                 </div>
                );
        }
}

export default CategoriesSearch;