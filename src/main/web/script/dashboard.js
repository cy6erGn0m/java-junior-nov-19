function createTransactionRow(date, amount, from, to) {
    var tr = document.createElement('tr');
    var dateCell = document.createElement('td');
    var amountCell = document.createElement('td');
    var fromCell = document.createElement('td');
    var toCell = document.createElement('td');

    dateCell.textContent = date;
    amountCell.textContent = amount;
    fromCell.textContent = from;
    toCell.textContent = to;

    tr.appendChild(dateCell);
    tr.appendChild(amountCell);
    tr.appendChild(fromCell);
    tr.appendChild(toCell);

    return tr;
}

function transactionsLoaded(transactions) {
    var tbody = document.getElementById('transactions-list');
    clearNode(tbody);

    for (var index = 0; index < transactions.length; index++) {
        var transaction = transactions[index];
        var date = new Date(transaction.time).toISOString();
        var amount = transaction.amount.toString();
        var from = transaction.origin.login;
        var to = transaction.receiver.login;

        var tr = createTransactionRow(date, amount, from, to);
        tbody.appendChild(tr);
    }
}

function loadTransactionsFailed() {
    var tbody = document.getElementById('transactions-list');
    clearNode(tbody);

    var tr = document.createElement('tr');
    var errorMessageCell = document.createElement('td');
    errorMessageCell.colSpan = 4;
    errorMessageCell.textContent = 'Failed to load transactions';

    tr.appendChild(errorMessageCell);
    tbody.appendChild(tr);
}

function loadTransactions(id) {
    var request = new XMLHttpRequest();
    request.open("get", "/api/transactions/find?page=1&accountId=" + id, true);
    request.onreadystatechange = function (ev) {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var response = JSON.parse(request.responseText);
                transactionsLoaded(response.content);
            } else {
                loadTransactionsFailed();
            }
        }
    };
    request.send();
}

function clearNode(node) {
    while (node.hasChildNodes()) {
        node.removeChild(node.firstChild);
    }
}
