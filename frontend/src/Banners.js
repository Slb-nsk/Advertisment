import React, {Component} from 'react';

import BanList from "./BanList";

import client from "./client";


class Banners extends Component {

  constructor() {
        super()

        this.state = {
                         id : 0,
                       name : '',
                      price : 0,
                   category : '',
                    content : '',
                initialData : [],
                errorMessage: ''
                  }
   this.handleChange = this.handleChange.bind(this)
   this.handleSubmit = this.handleSubmit.bind(this)
   this.handleDelete = this.handleDelete.bind(this)

}

  componentDidMount() {

    client
      .get("/banners")
      .then(response => {
            const newData = response.data.map(d => {
               return {
                    id: d.id,
                  name: d.name,
               content: d.content,
                 price: d.price,
              category: d.category
                      };
              });

        	this.setState({initialData: newData});
           })
      .catch(error => console.log(error));

  }

  updateData (config) {
  this.setState({id: config.id})
  this.setState({name: config.name})
  this.setState({price: config.price})
  this.setState({category: config.category})
  this.setState({content: config.content})
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
      .post(`/banner`, {id: this.state.id,
               name: this.state.name,
               content: this.state.content,
               price: this.state.price,
               category: this.state.category})
      .then(res => console.log(res))
      .catch(error =>{this.setState({errorMessage: error.response.data})});
     } else {
    client
      .put(`/banner`, {id: this.state.id,
               name: this.state.name,
               content: this.state.content,
               price: this.state.price,
               category: this.state.category})
      .then(res => console.log(res))
      .catch(error =>{this.setState({errorMessage: error.response.data})});
 
      }

  }

  handleDelete(event) {
event.preventDefault();
    client
      .delete(`/banner/${this.state.id}
`)
      .then(res => console.log(res))
      .catch(error =>{this.setState({errorMessage: error.response.data})});
   }


  render() {

   var choosenBanner = {"id": this.state.id, 
                        "name": this.state.name, 
                        "price": this.state.price,
                        "category": this.state.category,
                        "content": this.state.content}


         return (<div>
                 <table><tbody><tr>
                 <td><div><h1>Banners</h1>
                          <BanList updateData={this.updateData.bind(this)}
                                   data={this.state.initialData}/>
                 </div></td>
                 <td><b>{this.state.name}</b> ID: <b>{this.state.id}</b><hr/>
                   <form onSubmit = {this.handleSubmit}>
                   <p>Name <input type="text" name="name" value={this.state.name} placeholder={this.state.name} onChange={this.handleChange}/></p>
                   <p>Price <input type="number"step="0.01" min="0" name="price" value={this.state.price} placeholder={this.state.price} onChange={this.handleChange}/></p>
                   <p>Category <input type="text" name="category" value={this.state.category} placeholder={this.state.category} onChange={this.handleChange}/></p>
                   <p>Text <textarea type="text" name="content" value={this.state.content} placeholder={this.state.text} onChange={this.handleChange}/></p>
                   <br/>
                          <button type="submit">Save</button></form>
                         <form onSubmit = {this.handleDelete}><button type="submit">Delete</button>
                   </form><br/>
                 <h1>{this.state.errorMessage}</h1></td>
                 </tr></tbody></table>
               </div>);
      
      }

}

export default Banners;