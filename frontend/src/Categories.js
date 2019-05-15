import React, {Component} from 'react';

import CatList from "./CatList";

import client from "./client";


class Categories extends Component {

  constructor() {
        super()

        this.state = {
                         id : 0,
                       name : '',
                   req_name : '',
                initialData : [],
                errorMessage: ''
                  }
   this.handleChange = this.handleChange.bind(this)
   this.handleSubmit = this.handleSubmit.bind(this)
   this.handleDelete = this.handleDelete.bind(this)

}

componentDidMount() {

   client
      .get("/categories")
      .then(response => {
            const newData = response.data.map(c => {
               return {
                    id: c.id,
                  name: c.name,
               req_name: c.req_name
                      };
              });

        	this.setState({initialData: newData});
           })
      .catch(error => console.log(error));

}

  updateData (config) {
  this.setState({id: config.id})
  this.setState({name: config.name})
  this.setState({req_name: config.req_name})
          }


  handleChange(event) {
    const {name, value} = event.target
    this.setState({
        [name]: value
    })
}

handleSubmit(event) {
event.preventDefault();

   if (this.state.id == 0) {
    client
      .post(`/category`, {id: this.state.id,
               name: this.state.name,
               req_name: this.state.req_name})
      .then(res => console.log(res))
      .catch(error =>{this.setState({errorMessage: error.response.data})});
     } else {
    client
      .put(`/category`, {id: this.state.id,
               name: this.state.name,
               req_name: this.state.req_name})
      .then(res => console.log(res))
      .catch(error =>{this.setState({errorMessage: error.response.data})});
    
      }

  }

    handleDelete(event) {
  event.preventDefault();
      client
        .delete(`/category/${this.state.id}
  `)
        .then(res => console.log(res))
        .catch(error =>{this.setState({errorMessage: error.response.data})});
     }



  render() {

                 
   var choosenCategory = {"id": this.state.id, 
                          "name": this.state.name, 
                          "req_name": this.state.req_name}

         return (
                 <table><tbody><tr>
                 <td><div><h1>Categories</h1>
                          <CatList updateData={this.updateData.bind(this)}
                                   data={this.state.initialData}/>
                 </div></td>
                 <td><b>{this.state.name}</b> ID: <b>{this.state.id}</b><hr/>
                   <form onSubmit = {this.handleSubmit}>
                   <p>Name <input type="text" name="name" value={this.state.name} placeholder={this.state.name} onChange={this.handleChange}/></p>
                   <p>Request ID <input type="text" name="req_name" value={this.state.req_name} placeholder={this.state.req_name} onChange={this.handleChange}/></p>
                   <button type="submit">Save</button></form>
                    <form onSubmit = {this.handleDelete}><button type="submit">Delete</button>
                     </form><br/>
                     <h1>{this.state.errorMessage}</h1></td>
                      </tr></tbody></table>
                 
                );
      
      }

}

export default Categories;
