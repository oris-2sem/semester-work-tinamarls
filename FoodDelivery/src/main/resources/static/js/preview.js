let previewContainer = document.querySelector('.food-preview-container');
let previewBox = previewContainer.querySelectorAll('.food-preview');

document.querySelectorAll('.meals .meal .op').forEach(op =>{
    op.onclick = () =>{
        previewContainer.style.display = 'flex';
        let name = op.getAttribute('data-name');
        previewBox.forEach(preveiw =>{
            let target = preveiw.getAttribute('data-target');
            console.log(name + target)
            if(name === target){
                preveiw.classList.add('active');
            }
        });

    };
});

previewContainer.querySelector('#close-preview').onclick = () =>{
    previewContainer.style.display = 'none';
    previewBox.forEach(close =>{
        close.classList.remove('active');
    });
};