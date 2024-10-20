function getRandomInt() {
    return Math.floor(Math.random() * (50 - 5 + 1)) + 5
}

export const randomData = () => ({
    labels: [
        'January' + getRandomInt(),
        'February',
        'March',
        'April',
        'May',
    ],
    datasets: [
        {
            label: 'Data One',
            backgroundColor: '#f87979',
            data: [
                getRandomInt(),
                getRandomInt(),
                getRandomInt(),
                getRandomInt(),
                getRandomInt(),
            ]
        }
    ]
})

export const options = {
    responsive: true,
    maintainAspectRatio: false
}
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}


export const generateData = (data: any) => {
    const labels = data[0].data.map((item: any) => item.label);
    const backgroundColors = data[0].data.map((item: any) => item.color || getRandomColor());
    const dataset = {
        label: data[0].title,
        backgroundColor: backgroundColors,
        data: data[0].data.map((item: any) => item.value)
    };

    return {
        labels: labels,
        datasets: [dataset]
    };
}