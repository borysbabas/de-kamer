// adds id to objects in chart
let curretId = 0;

export const formatData = (data: any) => {
    curretId = 0;
    const type = findType(data[0]);
    const formatData = data.map((item: any) => {
        const value = type === "Members" ? item.memberCount : item.affiliationVotes;
        return constructObject(item.affiliationName, value, item?.color);
    });
    return type ? { name: type, formatData } : null;
}

function findType(item: any) {
    if (item?.affiliationName) {
        if (item.hasOwnProperty('memberCount')) return "Members";
        if (item.hasOwnProperty('affiliationVotes')) return "Votes";
    }
    return null;
}

function constructObject(itemName: string, itemValue: number, color?: string) {
    return {
        id: curretId++,
        itemName,
        itemValue,
        color: color || getRandomColor()
    }
}
export const normalizeData = (data: any) => {
    return [
        {
            name: data.name ? data.name : 'Count',
            data: data.formatData.map((item: any) => {
                return {
                    x: item.itemName,
                    y: item.itemValue,
                    fillColor: item.color ? item.color : getRandomColor(),
                    id: item.id,
                    hidden: false,
                }
            })
        }
    ];
}

export const sortData = (dataset: any, order: string) => {
    const sortedData = dataset[0].data.sort((a: any, b: any) => order === 'asc' ? b.y - a.y : a.y - b.y);
    return [{ name: dataset[0].name, data: sortedData }];
}
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
export const hideHighlight = (dataset: any, id: number) => {
    const highlightedData = dataset[0].data.map((item: any) => {
        return {
            ...item,
            fillColor: item.id === id ? '#000000' : item.fillColor
        }
    });
    return [{ name: dataset[0].name, data: highlightedData }];
}
