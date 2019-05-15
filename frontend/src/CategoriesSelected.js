import React from 'react';


export default ({filterText, data, updateData}) => {


const categoriesList = []

data.forEach((category) => {
      if (category.name.toLowerCase().includes(filterText)) {
      categoriesList.push(<li key={category.id} onClick={() => updateData(category)}>{category.name}</li>)
      }

   })

         return (<div>
                           <ul>{categoriesList}</ul>
                 </div>
                );
}