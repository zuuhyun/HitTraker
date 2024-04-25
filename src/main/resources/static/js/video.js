// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/videos/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/videos');
            });
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/videos/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                length: document.getElementById('length').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/videos/${id}`);
            });
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/api/videos', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                length: document.getElementById('length').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/videos');
            });
    });
}