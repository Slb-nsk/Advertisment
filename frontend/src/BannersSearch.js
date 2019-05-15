import React, {Component} from 'react';

class BannersSearch extends Component {

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
                         placeholder="Enter banner name..." /><br/>
                 </div>
                );
        }
}

export default BannersSearch;