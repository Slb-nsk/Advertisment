import React, {Component} from 'react';
import CategoriesSearch from './CategoriesSearch';
import CategoriesSelected from './CategoriesSelected';


class CatList extends Component {

  constructor(props) {
        super(props)

        this.state = {
                 filterText : ''
                  }

       this.updateFilterText = this.updateFilterText.bind(this)
       }

  updateFilterText (config) {
  this.setState({filterText: config})
         }

  render() {

   const data = this.props.data;
         return (<div>
                  <CategoriesSearch updateFilterText={this.updateFilterText}
                                 filterText={this.state.filterText}/>
                  <br/>
                  <CategoriesSelected updateData={this.props.updateData}
                                    filterText={this.state.filterText}
                                    data={data}/>
                  <br/>
                  <button onClick={() => this.props.updateData({"id":0,
                                                                "name":"",
                                                             "req_name":""})}>Create new category</button>

                 </div>
                );
      
     }


}


export default CatList;
