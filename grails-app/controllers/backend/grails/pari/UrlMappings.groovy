package backend.grails.pari

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/api/payments" (controller: 'ApiPayment', action: 'payments')
        "/api/payments/historic" (controller: 'ApiPayment', action: 'paymentsHistoricByUserByType')
        "/api/top/user/investment" (controller: 'ApiPayment', action: 'topUserWithMiseMax')
        "/api/movement/after/match" (controller: 'ApiTreasury', action: 'mvntAfterMatch')
        "/api/solde/site" (controller: 'ApiTreasury', action: 'solde')

        "/api/paris" (controller: 'ApiPari', action: 'paris')
        "/api/pari" (controller: 'ApiPari', action: 'pari')
        "/api/pari/custom" (controller: 'ApiPari', action: 'paricustom')
        "/api/pari/details" (controller: 'ApiPari', action: 'pariwithdetails')
        "/api/pari/statistic" (controller: 'ApiPari', action: 'paristatistic')
        "/api/pari/movement" (controller: 'ApiPari', action: 'changeDetailPariFinishedAndInsertMvnt')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
