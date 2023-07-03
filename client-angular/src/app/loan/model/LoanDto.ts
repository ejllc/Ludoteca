import { Client } from "src/app/client/model/Client";
import { Game } from "src/app/game/model/Game";

export class LoanDto {
    id: number;
    client: Client;
    game: Game;
    startDate: Date;
    endDate: Date;
}