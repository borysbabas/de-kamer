export function formatNubmer(number){
    const numberAsString = number.toString()
    let formatted = "";
    for( let i = numberAsString.length-1 , count=1;i>=0;i--,count++){
        formatted =numberAsString[i]+formatted;
        if (count%3===0&&i!==0){
            formatted="."+formatted;
        }

    }
    return formatted;
}