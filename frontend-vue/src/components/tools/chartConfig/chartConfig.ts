function getRandomInt() {
    return Math.floor(Math.random() * (50 - 5 + 1)) + 5
}

export const options = {
    responsive: true,
    maintainAspectRatio: false,
    indexAxis: 'y',
    scales: {
        x: {
            beginAtZero: true
        },
    }
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
    const labels = data.map((item: any) => item.affiliationName);
    const backgroundColors = data.map(() => getRandomColor());
    const dataset = {
        label: 'Affiliation Members Count',
        backgroundColor: backgroundColors,
        data: data.map((item: any) => item.memberCount)
    };

    return {
        labels: labels,
        datasets: [dataset]
    };
};

export const generateData2 = (data: any) => {
    const labels = data.map((item: any) => item.affiliationName);
    const dataset = [];
    data.forEach((item: any) => {
    const dataItem = {
        label: item.affiliationName,
        data: [item.memberCount],
        backgroundColor: getRandomColor()
    };
    dataset.push(dataItem);
});
    return {
        labels: data.map((item: any) => item.affiliationName),
        datasets: dataset
    }
}
