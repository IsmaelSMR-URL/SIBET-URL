const express = require('express')
const cors = require('cors')

const app = express()
const port = 3000

app.use(
    express.urlencoded({
        extended: true
    })
)

app.use(express.json({
    type:"*/*"
}))

app.use(cors());

///////

let transactionArray = []

app.get('/transactions', (req, res) =>{
    res.send(JSON.stringify(transactionArray));
    //res.send('GET request to the homepage')
})

app.post('/transactions', (req, res) =>{
    let transaction = req.body;
    transactionArray.push(transaction);
    res.send(JSON.stringify("Guardado"));
    console.log(transactionArray);
    //res.send('POST request to the homepage')
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})