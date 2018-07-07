var xmlhttp;
if (window.XMLHttpRequest) {
    xmlhttp = new XMLHttpRequest();
} else {
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

window.onload = function (ev) {
    getBooks();
};

function getBooks() {
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            var tbody = document.getElementById("tbBooks");
            var result = xmlhttp.responseText;
            // var json = eval('(' + result + ')');
            var json = JSON.parse(result);
            console.log(json);
            for (var k in json) {
                tbody.appendChild(getData(json[k]));
            }
        }
    };
    xmlhttp.open("GET", "/books", true);
    xmlhttp.send();
}

function getData(h) {
    var row = document.createElement("tr");
    var idCell = document.createElement('td');
    row.appendChild(idCell);
    idCell.innerHTML = h.id;
    var nameCell = document.createElement('td');
    row.appendChild(nameCell);
    nameCell.innerHTML = h.bookName;
    var priceCell = document.createElement('td');
    row.appendChild(priceCell);
    priceCell.innerHTML = h.price;
    var authorCell = document.createElement('td');
    row.appendChild(authorCell);
    authorCell.innerHTML = h.author;
    var operationCell = document.createElement('td');
    row.appendChild(operationCell);
    //创建按钮
    var searchBtn = document.createElement("input");
    searchBtn.setAttribute('type', 'button');
    searchBtn.setAttribute('value', '详情');
    search(searchBtn, h.id);
    operationCell.appendChild(searchBtn);

    var deleteBtn = document.createElement("input");
    deleteBtn.setAttribute("type", "button");
    deleteBtn.setAttribute('value', '删除');
    del(deleteBtn, h.id);
    operationCell.appendChild(deleteBtn);
    return row;
}

function search(btn, id) {
    btn.onclick = function () {
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                var json = eval('(' + xmlhttp.responseText + ')');
                alert("bookName:" + json.bookName + "    price:" + json.price + "    author:" + json.author);
            }
        };
        xmlhttp.open("GET", "/books?id=" + id, true);
        xmlhttp.send();

    }
}

function del(btn, id) {
    btn.onclick = function () {
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                var json = eval('(' + xmlhttp.responseText + ')');
                if (json.msg === "SUCCESS") {
                    location.reload();
                }
            }
        };
        xmlhttp.open("DELETE", "/books/" + id, true);
        xmlhttp.send();
    };
}