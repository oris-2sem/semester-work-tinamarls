const moreButtons = document.querySelectorAll('.more');
moreButtons.forEach((button) => {
    button.addEventListener('click', (event) => {
        const form = event.currentTarget.nextElementSibling;
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    });
});

const sendButtons = document.querySelectorAll('content .sendForm');
sendButtons.forEach((button) => {
    button.addEventListener('click', (event) => {
        const form = event.currentTarget.closest('.content .form');
        form.style.display = 'none';
    });
});

function searchTable() {
    let input = document.getElementById('search-input');
    let filter = input.value.toUpperCase();
    let table = document.getElementById('productTable');
    let rows = table.getElementsByTagName('tr');

    for (let i = 1; i < rows.length; i++) {
        let cells = rows[i].getElementsByTagName('td');
        let display = false;

        for (let j = 0; j < cells.length; j++) {
            let cell = cells[j];
            if (cell) {
                if (cell.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    display = true;
                    break;
                }
            }
        }

        rows[i].style.display = display ? '' : 'none';
    }
}


