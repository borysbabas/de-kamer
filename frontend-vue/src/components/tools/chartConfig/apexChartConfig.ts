export const formatData = (data: any) => {
    const type = findType(data[0]);
    switch (type) {
        case "Members":
            return {
                name: 'Members',
                formatData: data.map((item: any) => {
                    return constructObject(item.affiliationName, item.memberCount, item?.color)
                })
            }
        case "Votes":
            return {
                name: 'Votes',
                formatData: data.map((item: any) => {
                    return constructObject(item.affiliationName, item.affiliationVotes, item?.color)
                })
            }
        default:
            return null
    }
}

function findType(item: any) {
    if (item?.hasOwnProperty('affiliationName') && item.hasOwnProperty('memberCount')) {
        return "Members"
    } else if (item?.hasOwnProperty('affiliationName') && item.hasOwnProperty('affiliationVotes')) {
        return "Votes"
    }
    return null
}

function constructObject(itemName: string, itemValue: number, color?: string) {
    return {
        itemName: itemName,
        itemValue: itemValue,
        color: color? color : getRandomColor()
    }
}





export const normalizeData = (data: any) => {
    console.log(data, 'data');
    const formatedData = [
        {
            name: data.name ? data.name : 'Count',
            data: data.formatData.map((item: any) => {
                return {
                    x: item.itemName,
                    y: item.itemValue,
                    fillColor: item.color? item.color : getRandomColor(),
                }
            })
        }
    ];
    return formatedData;
}

export const sortData = (dataset: any, order:string) => {
    if (order === 'asc') {
        const sortedData = dataset.map((item: any) => item.data).flat().sort((a: any, b: any) => b.y - a.y);
        return [
            {
                name: dataset[0].name,
                data: sortedData
            }];
    } else if (order === 'desc') {
        const sortedData = dataset.map((item: any) => item.data).flat().sort((a: any, b: any) => a.y - b.y);
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
