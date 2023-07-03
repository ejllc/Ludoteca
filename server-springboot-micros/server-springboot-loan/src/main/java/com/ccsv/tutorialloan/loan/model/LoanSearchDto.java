package com.ccsv.tutorialloan.loan.model;

import java.util.Date;

import com.ccsv.tutorialloan.common.pagination.PageableRequest;

/**
 * @author emlloren
 *
 */
public class LoanSearchDto {
    private PageableRequest pageable;
    private Long clientId;
    private Long gameId;
    private Date date;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
