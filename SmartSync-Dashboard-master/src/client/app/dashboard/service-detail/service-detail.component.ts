import {Component, OnInit, Input} from '@angular/core';
import {Service} from '../../shared/service/service';
import {ServiceService} from '../../shared/service/service.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';
import {GlobalVariables} from '../../shared/global-variables';
import {UserService} from '../../shared/user/user.service';
import {HouseholdService} from '../../shared/household/household.service';

@Component({
  moduleId: module.id,
  selector: 'service-detail-cmp',
  templateUrl: 'service-detail.component.html'
})

export class ServiceDetailComponent implements OnInit {
  @Input()
  service: Service;
  isEditing: boolean = false;

  constructor(private serviceService: ServiceService,
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private householdService: HouseholdService) {
  }

  ngOnInit(): void {
    this.isEditing = (this.route.data as any).value.isEditing === true;
    this.userService.getActiveUser()
      .then(user => {
        this.householdService.getActiveHousehold().then(house => {
          this.route.params
            .switchMap((params: Params) => this.serviceService.getService(user.userId, +params['id']))
            .subscribe(service => {
              if (service) {
                console.log(service);
                this.service = service;
                this.navigationComplete();
              } else {
                console.log(service);
                console.log('Bad route: ' + this.router.url);
                this.router.navigate(['dashboard/', 'services']);
              }
            });
        });
      });
  }

  cancel(): void {
    this.location.back();
  }

  save(): void {
    // TODO: Save service configuration
    console.log('Service configuration saved!');
    this.location.back();
  }

  private navigationComplete(): void {
    GlobalVariables.navigationState.next(false);
  }
}
