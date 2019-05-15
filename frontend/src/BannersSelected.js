import React from 'react';


export default ({filterText, data, updateData}) => {


const bannersList = []

data.forEach((banner) => {
      if (banner.name.toLowerCase().includes(filterText)) {
      bannersList.push(<li key={banner.id} onClick={() => updateData(banner)}>{banner.name}</li>)
      }
})  


         return (<div>
                           <ul>{bannersList}</ul>
                 </div>
                );
}