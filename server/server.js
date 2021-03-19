
const express = require('express');
const routes = require('./routes');

const app = express();

app.use(express.json());

app.use(routes);



app.listen(8080, function(){ console.log('Servidor Web rodando na porta 9090') });