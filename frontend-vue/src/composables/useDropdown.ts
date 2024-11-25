import {ref} from "vue";

export const options = [
    {label: 'Meest verkozen partijen', value: 'most_parties'},
    {label: 'Minst verkozen partijen', value: 'least_parties'},
    {label: 'Meest verkozen kandidaten', value: 'most_candidates'},
    {label: 'Minst verkozen kandidaten', value: 'least_candidates'}
];

export const selectedOption = ref(options[0].value);
const dropdownSelect = ref<HTMLSelectElement | null>(null);

export const triggerDropdown = () => {
    if (dropdownSelect.value) {
        dropdownSelect.value.click();
    }
};