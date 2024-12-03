export type Candidate = {
    candidateId: number;
    firstName?: string;
    lastName: string;
    gender?: string;
    initials: string;
    lastNamePrefix?: string;
    name?: string;
    locality: string;
    memberId: number;
    shortCode?: string;
    affiliationId: number;
    votes?: number;
}