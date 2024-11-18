


export const normalizeData = (data: any) => {
    const formatedData = [
        {
            name: data.name ? data.name : 'Count',
            data: data.map((item: any) => {
                return {
                    x: item.affiliationName,
                    y: item.memberCount,
                    fillColor: item.color? item.color : getRandomColor(),
                }
            })
        }
    ];
    return formatedData;
}

export const sortData = (dataset: any, order:string) => {
    console.log("sort", dataset.map((item: any) => item.data));
    if (order === 'asc') {
        const sortedData = dataset.map((item: any) => item.data).flat().sort((a: any, b: any) => a.y - b.y);
        return [
            {
                name: dataset[0].name,
                data: sortedData
            }];
    } else if (order === 'desc') {
        const sortedData = dataset.map((item: any) => item.data).flat().sort((a: any, b: any) => b.y - a.y);
        return [
            {
                name: dataset[0].name,
                data: sortedData
            }];
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
