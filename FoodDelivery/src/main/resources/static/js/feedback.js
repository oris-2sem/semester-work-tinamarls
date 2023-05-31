document.addEventListener("DOMContentLoaded", function() {
    findAllFeedback();
});

function findAllFeedback(){
    fetch('/feedback/all')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector(".feedback-table tbody");
            tableBody.innerHTML = "";

            data.forEach(feedback => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${feedback.date}</td>
                    <td>${feedback.stars}</td>
                    <td>${feedback.commentary}</td>
                `;

                tableBody.appendChild(row);
            })});
}

function addNewFeedback(){

    let form = document.getElementById("send-form");

    let stars = document.getElementById('stars').value;
    let commentary = document.getElementById('commentary').value;

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    let formData = {
        stars: stars,
        commentary: commentary
    };

    fetch('/feedback', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(formData)
    })
        .then(function(response) {
            if(!response.ok){
                throw new Error('Ошибка при выполнении запроса.');
            } else {
                form.reset();
                findAllFeedback();
            }
        })
        .then(function(data) {
            console.log('Ответ от сервера:', data);
        })
        .catch(function(error) {
            console.error('Ошибка при выполнении запроса:', error);
        });
}


