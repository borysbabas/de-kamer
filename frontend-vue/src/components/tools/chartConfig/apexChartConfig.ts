// adds id to objects in chart
// export const sortData = (dataset: any, order: string) => {
//     const sortedData = dataset[0].data.sort((a: any, b: any) => order === 'asc' ? b.y - a.y : a.y - b.y);
//     return [{ name: dataset[0].name, data: sortedData }];
// }
export const hideHighlight = (dataset: any, id: number) => {
    const highlightedData = dataset[0].data.map((item: any) => {
        const newState = item.id === id ?
            (item.state === 'show' ? 'highlighted' : item.state === 'highlighted' ? 'hidden' : 'show')
            : item.state;

        return {
            ...item,
            fillColor: newState === 'highlighted' ? '#000000' : item.hiddenValue.fillColor,
            state: newState,
            y: newState === 'hidden' ? 0 : item.hiddenValue?.y ?? item.y,
            x: newState === 'hidden' ? '' : item.hiddenValue?.x ?? item.x
        };
    });
    return [{ name: dataset[0].name, data: highlightedData }];
};