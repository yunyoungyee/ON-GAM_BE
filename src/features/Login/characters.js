export function createEmotionCharacter(type) {
    const character = document.createElement('div');
    character.className = `character ${type}`;

    // face 클래스는 캐릭터의 shape를 담당합니다.
    const face = document.createElement('div');
    face.className = `face ${type}`;

    // 눈(좌): pupil 클래스가 DOM 선택의 핵심입니다.
    const eyeLeft = document.createElement('div');
    eyeLeft.className = `eye eye-left`;
    const pupilLeft = document.createElement('div');
    pupilLeft.className = 'pupil';
    eyeLeft.appendChild(pupilLeft);

    // 눈(우)
    const eyeRight = document.createElement('div');
    eyeRight.className = `eye eye-right`;
    const pupilRight = document.createElement('div');
    pupilRight.className = 'pupil';
    eyeRight.appendChild(pupilRight);

    // 감정별 shape/style 적용
    applyEmotionShape(face, type);

    // 조립
    face.append(eyeLeft, eyeRight);
    character.append(face);

    return character;
}

function applyEmotionShape(face, type) {
    switch (type) {
        case 'angry':
            addDevilHorns(face);
            break;

        case 'anxious':
            face.classList.add('anxious');
            break;
    }
}

function addDevilHorns(face) {
    const hornLeft = document.createElement('div');
    const hornRight = document.createElement('div');

    hornLeft.className = 'devil-horn left';
    hornRight.className = 'devil-horn right';

    face.append(hornLeft, hornRight);
}


export function moveEyes(event) {
    // 💡 수정됨: 루프 밖의 pupil 변수를 사용하는 대신, 문서 전체에서 모든 동공을 찾습니다.
    const pupils = document.querySelectorAll('.pupil');

    pupils.forEach((pupil) => {
        // 동공의 부모 요소인 눈 (흰자)의 위치를 기준으로 계산해야 합니다.
        // 현재 rect는 동공의 위치를 반환하지만, 눈의 중심을 기준으로 계산해야 정확합니다.

        const eye = pupil.parentElement; // 눈 (흰자) 요소
        if (!eye) return; // 혹시 모를 오류 방지

        const eyeRect = eye.getBoundingClientRect();
        // 눈 흰자의 중앙점 계산
        const eyeCenterX = eyeRect.left + eyeRect.width / 2;
        const eyeCenterY = eyeRect.top + eyeRect.height / 2;

        const angle = Math.atan2(event.clientY - eyeCenterY, event.clientX - eyeCenterX);

        const maxMovement = 8; // 눈알 움직임 최대치
        const x = Math.cos(angle) * maxMovement;
        const y = Math.sin(angle) * maxMovement;

        // 동공의 움직임이 눈 흰자의 중심을 기준으로 이동하도록 transform 적용
        // (pupil이 CSS로 초기 중앙 정렬되어 있다고 가정하고, 추가 이동만 적용합니다.)
        pupil.style.transform = `translate(-10%, -10%) translate(${x}px, ${y}px)`;
    });
}
export function scatterPosition(characterArea, character) {
    const areaWidth = characterArea.clientWidth;
    const areaHeight = characterArea.clientHeight;

    const maxX = areaWidth - 150;  // 캐릭터 크기 고려
    const maxY = areaHeight - 150;

    const x = Math.random() * maxX;
    const y = Math.random() * maxY;

    character.style.left = x + "px";
    character.style.top = y + "px";
}