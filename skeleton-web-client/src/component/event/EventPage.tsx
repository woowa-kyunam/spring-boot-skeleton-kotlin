import * as React from 'react';
import { RouteComponentProps } from 'react-router';
import EventCategories from './EventCategories';
import EventList from './EventList';

interface EventPageProps {
  category: string;
}
export default class EventPage extends React.Component<
  RouteComponentProps<EventPageProps>,
  any
> {
  public render() {
    const { category } = this.props.match.params || 'all';
    return (
      <>
        <EventCategories />
        <EventList category={category} />
      </>
    );
  }
}
