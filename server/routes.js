const { Router } = require('express');

const routes = Router();

const cardapio = []

routes.get('/', (req, res) => {
    return res.json(cardapio);
  }); 
  
  routes.get('/:id', (req, res) => {
    const produto = cardapio[req.params.id]  
    return res.json(produto);
  }); 
  
routes.post('/',(req, res) => { 
    cardapio.push(req.body);
    return res.json(cardapio)
}); 

routes.put('/:id', (req, res) => { 
    cardapio[req.params.id] = req.body; 
    console.log( cardapio)
    return res.json(cardapio);
}); 

routes.delete('/:id', (req, res) => {    
    cardapio.splice(req.params.id, 1);
    return res.send();
}); 


module.exports = routes;
