import React, {Component} from 'react';
import BannersSearch from './BannersSearch';
import BannersSelected from './BannersSelected';

class BanList extends Component {


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
                  <BannersSearch updateFilterText={this.updateFilterText}
                                 filterText={this.state.filterText}/>
                  <br/>
                  <BannersSelected updateData={this.props.updateData}
                                    filterText={this.state.filterText}
                                    data={data}/>
                  <br/>
                  <button onClick={() => this.props.updateData({"id":0,
                                                                "name": "",
                                                                "price":0.00,
                                                             "category":"",
                             "content": "Lorem ipsum"})}>Create new banner</button>

                 </div>
                );
      
     }
}


export default BanList;