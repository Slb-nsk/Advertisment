import React, {Component} from 'react';

import Header from "./Header"
import Banners from "./Banners"
import Categories from "./Categories"

class App extends Component {

  constructor() {
        super()
     this.state = {whatWeWorkWith: ``}
    }

  updateData = (value) => {
   this.setState({ whatWeWorkWith: value })
   }


  render() {
    const whatWeWorkWith = this.state.whatWeWorkWith
     switch (whatWeWorkWith) {

       case ('Banners'):
         return ( <div>
                 <Header updateData={this.updateData} />
                 <Banners/>
                 </div>
                );

       case ('Categories'):
         return ( <div>
                 <Header updateData={this.updateData} />
                 <Categories/>
                 </div>
                );

       default :
         return ( <div>
                 <Header updateData={this.updateData} />
                 </div>
                );
      }
  }

}

export default App;
