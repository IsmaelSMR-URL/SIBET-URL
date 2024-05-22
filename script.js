const formElement = document.getElementById("saveType");

formElement.addEventListener("submit", (event) => {
    event.preventDefault();
    let idType = document.getElementById("idType").value;
    let typeName = document.getElementById("typeName").value;
    let transaction = { idType: idType, typeName: typeName };
    let transactionJson = JSON.stringify(transaction);
    console.log(transactionJson);

    fetch('http://localhost:3000/transactions', {
        method: 'Post',
        body: transactionJson
    })
        .then(() => {
            // Obtener la lista actualizada de tipos de productos
            fetch('http://localhost:3000/transactions')
                .then(response => response.json())
                .then(data => {
                    // Actualizar la tabla con los nuevos datos
                    updateTypeTable(data);
                })
                .catch(error => console.error('Error:', error));
        })
        .catch(error => console.error('Error:', error));
});

function updateTypeTable(data) {
    const tableBody = document.querySelector('.datatable tbody');
    tableBody.innerHTML = ''; // Limpiar el contenido anterior

    data.forEach(typeObject => {
        typeObject.forEach(type => {
            const row = document.createElement('tr');

            const idCell = document.createElement('td');
            idCell.textContent = type.idType;
            row.appendChild(idCell);

            const nameCell = document.createElement('td');
            nameCell.textContent = type.typeName;
            row.appendChild(nameCell);

            const optionsCell = document.createElement('td');
            // Agrega los botones de opciones (ver, editar, eliminar) aquí
            row.appendChild(optionsCell);

            tableBody.appendChild(row);
        });
    });
}